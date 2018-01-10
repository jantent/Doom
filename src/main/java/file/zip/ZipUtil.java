package file.zip;

import file.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static void main(String[] args) throws Exception {
        String afile = "F:/zip/a.cer";
        String bfile = "F:/zip/b.cer";
        String acert = "MIIBiDCCAS+gAwIBAgIIE1AAEAAAAAYwDAYIKoEcz1UBg3UFADAUMRIwEAYDVQQDDAljZXNod2VocnUwIBcNMTcxMjE4MDExMjE1WhgPMjA1MjEyMDkwMTEyMTVaMBgxFjAUBgNVBAMMDWJkcGtpUkHmtYvor5UwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAQaEJnn/0FvbxAJ8pB0Z0KLfZOix/TvJfLXiUQSB6qqTv+Yf7ywpzsNQALM1cR/hF7xoHvHFAavL+yLtX1t/8uRo2MwYTAPBgNVHRMBAf8EBTADAQEAMA4GA1UdDwEB/wQEAwIAwDAdBgNVHQ4EFgQUCdv8VMdx4MrGb6vvxiy3wPN1zfQwHwYDVR0jBBgwFoAUBcGSNdfCN2oMklKmNb2BIk+V3mQwCgYIKoEcz1UBg3UDRwAwRAIgargOQrgzLgncqLBrra/FSRGMtsQTQg64es3wO3d7YD8CIDaWVZr06aLEWKDcfCpTuNCIX+sEc3E3V6g4DjrE8+Ii";
        FileUtil.writeFileContent(afile,acert);
        FileUtil.writeFileContent(bfile,acert);
        String dstFile = "F:/zip/cert.zip";
        List<String> strings = new ArrayList<>();
        strings.add(afile);
        strings.add(bfile);
        zip(strings,dstFile,"a",true,"");
        File file = new File(afile);
        file.delete();
        file = new File(bfile);
        file.delete();

    }

    public static void zip(List<String> srcFiles, String dstFile, String comment, boolean ifFullPath, String srcFilesPrefix)
            throws IOException {
        if (srcFiles.size() == 0)
            return;
        File fileDst = new File(dstFile);
        File parentDir = fileDst.getParentFile();
        if (!(parentDir.exists())) {
            parentDir.mkdirs();
        }
        if (fileDst.exists())
            fileDst.delete();
        fileDst.createNewFile();

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileDst));
        zos.setMethod(8);

        if (comment != null) {
            zos.setComment(comment);
        }

        DataOutputStream dos = new DataOutputStream(zos);

        for (int i = 0; i < srcFiles.size(); ++i) {
            String entryPath = srcFiles.get(i);
            File fileEntry;
            if (srcFilesPrefix != null)
                fileEntry = new File(srcFilesPrefix + entryPath);
            else {
                fileEntry = new File(entryPath);
            }

            if (ifFullPath)
                zos.putNextEntry(new ZipEntry(entryPath));
            else {
                zos.putNextEntry(new ZipEntry(fileEntry.getName()));
            }

            FileInputStream fis = new FileInputStream(fileEntry);

            byte[] buff = new byte[8192];
            int len = 0;
            while (true) {
                len = fis.read(buff);
                if (len == -1)
                    break;
                if (len == 0) {
                    break;
                }
                dos.write(buff, 0, len);
            }

            zos.closeEntry();
            fis.close();
        }
        dos.close();
        zos.close();
    }
}
