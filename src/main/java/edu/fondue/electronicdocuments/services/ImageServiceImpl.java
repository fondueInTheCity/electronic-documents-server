package edu.fondue.electronicdocuments.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final String SIGNATURE_NAME = "signature.png";

    private final StorageService storageService;

    @Override
    public void saveSignaturePng(final Long userId, final String text) {
        final byte[] imgSource = generateUserSignatureImg(text);
        storageService.uploadSignaturePng(imgSource, userId, SIGNATURE_NAME);
    }

    @SneakyThrows
    private byte[] generateUserSignatureImg(final String text) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        final Font font = new Font("Arial", Font.PLAIN, 24);

        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        final int width = fm.stringWidth(text);
        final int height = fm.getHeight();

        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( img, "png", baos );
        baos.flush();
        baos.close();

        return baos.toByteArray();
    }
}
