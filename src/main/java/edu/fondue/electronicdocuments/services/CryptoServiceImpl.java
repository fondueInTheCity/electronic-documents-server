package edu.fondue.electronicdocuments.services;

import com.aspose.pdf.PKCS1;
import com.aspose.pdf.facades.PdfFileSignature;
import edu.fondue.electronicdocuments.utils.Properties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final StorageService storageService;

    private final Properties properties;

    @Override
    @SneakyThrows
    public void addSignatureToPdf(final String path, final Long userId, final String fileName, final String reason) {
        final byte[] file = storageService.download(path);
        final String localPath = format("%s%s", properties.getLocalStorageInput(), fileName);
        final String localPathOutput = format("%s%s", properties.getLocalStorageOutput(), fileName);
        final FileOutputStream outputStream = new FileOutputStream(localPath);
        outputStream.write(file);
        outputStream.flush();
        outputStream.close();

        PdfFileSignature pdfSign = new PdfFileSignature();
        pdfSign.bindPdf(localPath);
        final int length = pdfSign.getDocument().getForm().getFields().length;
        java.awt.Rectangle rect = new java.awt.Rectangle(100, 60 * length, 100, 50);
        final byte[] data = storageService.download(format("electronic-documents/users/%d/signature.png", userId));
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        final String signatureImgPath = format("%s%s", properties.getLocalStorageInput(), "signature.png");
        final File signatureImg = new File(signatureImgPath);
        ImageIO.write(bImage2, "png", signatureImg );
        pdfSign.setSignatureAppearance(signatureImgPath);
        PKCS1 signature = new PKCS1(format("%s%s", properties.getLocalStorageKeys(), "mycert2.pfx"), "123456");
        pdfSign.sign(1, reason, "", "", true, rect, signature);
        pdfSign.save(localPathOutput);
        final FileInputStream fileInputStream = new FileInputStream(localPathOutput);
        storageService.change(path, fileInputStream);
        fileInputStream.close();

        Files.deleteIfExists(Paths.get(signatureImgPath));
        Files.deleteIfExists(Paths.get(localPath));
        Files.deleteIfExists(Paths.get(localPathOutput));
    }
}
