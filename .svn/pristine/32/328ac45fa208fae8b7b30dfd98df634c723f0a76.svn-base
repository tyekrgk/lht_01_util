package lht.wangtong.core.utils.fullseach;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2014/9/18.
 */
public class Directorys {

    /**
     * 获取指定目录下的所有的文件（不包括文件夹），采用了递归
     *
     * @param obj
     * @return
     */
    public static Collection<File> getFiles(Object obj) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        Collection<File> files = new ArrayList<File>();
        if (directory.isFile()) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                files.addAll(getFiles(fileOne));
            }
        }
        return files;
    }
}
