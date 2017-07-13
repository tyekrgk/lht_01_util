package lht.wangtong.core.utils.fullseach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationUtils {
    public static byte[] convertToByte(Serializable serializable)
            throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(serializable);
            byte[] arrayOfByte = bout.toByteArray();
            return arrayOfByte;
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (Exception ignored) {
            } finally {
                try {
                    bout.close();
                } catch (Exception ignored) {
                }
            }
        }

    }

    public static Serializable convertToSerializable(byte[] bytes)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bin = null;
        ObjectInputStream ois = null;
        try {
            bin = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bin);
            Serializable localSerializable = (Serializable) ois.readObject();
            return localSerializable;
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (Exception ignored) {
            } finally {
                try {
                    if (bin != null)
                        bin.close();
                } catch (Exception ignored) {
                }
            }
        }

    }
}