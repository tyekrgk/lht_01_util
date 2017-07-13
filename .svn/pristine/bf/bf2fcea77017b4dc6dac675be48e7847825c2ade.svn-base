package lht.wangtong.core.utils.fullseach;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;
import java.nio.charset.Charset;


public class Utils {

    public static String getTextByUrl(java.net.URL url) {
        try {
            return (new org.apache.tika.Tika()).parseToString(url);
        } catch (Exception e) {
            throw new UnsupportedOperationException("提取文本内容失败.", e);
        }
    }

    public static String getTextByFile(String file) {
        return getTextByFile(new File(file));
    }

    public static String getTextByFile(File file) {
        try {
            return (new org.apache.tika.Tika()).parseToString(file);
        } catch (Exception e) {
            throw new UnsupportedOperationException("提取文本内容失败.", e);
        }
    }

    public static String getTextByStream(InputStream inputStream) {
        try {
            return (new org.apache.tika.Tika()).parseToString(inputStream);
        } catch (Exception e) {
            throw new UnsupportedOperationException("提取文本内容失败.", e);
        }
    }

    private static String getTextByStream(InputStream inputStream, Parser parser) {
        //StringWriter stringWriter = new StringWriter();
        java.io.Writer writer = new java.io.StringWriter();
        org.xml.sax.ContentHandler handler = new BodyContentHandler(writer);

        try {
            parser.parse(inputStream, handler, new Metadata(), new ParseContext());
        } catch (Exception e) {
            throw new UnsupportedOperationException("提取文本内容失败.", e);
        } finally {
            try {
                writer.close();
                //stringWriter.close();
            } catch (IOException e) {
                throw new UnsupportedOperationException("提取文本内容失败.", e);
            }
        }

        return writer.toString();
    }

    public static String getTextByHtml(String html) {
        return getTextByStream(new ByteArrayInputStream(html.getBytes()), new org.apache.tika.parser.html.HtmlParser());
    }

    public static String getTextByHtml(String html, Charset charset) {
        return getTextByStream(new ByteArrayInputStream(html.getBytes(charset)), new org.apache.tika.parser.html.HtmlParser());
    }
}
