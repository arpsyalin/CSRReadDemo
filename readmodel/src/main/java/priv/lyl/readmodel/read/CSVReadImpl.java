package priv.lyl.readmodel.read;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import priv.lyl.readmodel.interf.IFileRead;
import priv.lyl.readmodel.model.FileResource;

public class CSVReadImpl implements IFileRead {
    FileResource fileResource;
    public static final String ENCODE = "UTF-8";
    private FileInputStream mFileInputStream = null;
    private InputStreamReader mInputStreamReader = null;
    private BufferedReader mBufferedReader = null;

    public CSVReadImpl(FileResource fileResource) throws Exception {
        this.fileResource = fileResource;
        mFileInputStream = new FileInputStream(fileResource.getUri());
        mInputStreamReader = new InputStreamReader(mFileInputStream, ENCODE);
        mBufferedReader = new BufferedReader(mInputStreamReader);
    }

    @Override
    public List<List<String>> readFile() {
        List<List<String>> data = new ArrayList();
        boolean isEnd = false;
        try {
            while (!isEnd) {
                String string = readLine();
                if (string != null) {
                    List<String> csv = fromCSVLine2Array(string);
                    data.add(csv);
                } else {
                    isEnd = true;
                }
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件里面的一行
     *
     * @return
     * @throws Exception
     */
    public String readLine() throws Exception {
        StringBuffer rlSB = new StringBuffer();
        boolean bReadNext = true;
        while (bReadNext) {
            //
            if (rlSB.length() > 0) {
                rlSB.append("\r\n");
            }
            //先读取一行
            String strReadLine = mBufferedReader.readLine();
            // 读取的这行数据为空 则返回空
            if (strReadLine == null) {
                return null;
            }
            //如果读取的这行不为空则添加
            rlSB.append(strReadLine);
            // 如果双引号是奇数的时候继续读取。考虑有换行的情况。
            if (countChar(rlSB.toString(), '"', 0) % 2 == 1) {
                bReadNext = true;
            } else {
                bReadNext = false;
            }
        }
        return rlSB.toString();
    }


    /**
     * 把CSV文件的一行转换成字符串数组。不指定数组长度。
     */
    public static ArrayList fromCSVLine2Array(String source) {
        if (source == null || source.length() == 0) {
            return new ArrayList();
        }
        int currentPosition = 0;
        int maxPosition = source.length();
        int nextComma = 0;
        ArrayList rtnArray = new ArrayList();
        while (currentPosition < maxPosition) {
            nextComma = nextComma(source, currentPosition);
            rtnArray.add(nextToken(source, currentPosition, nextComma));
            currentPosition = nextComma + 1;
            if (currentPosition == maxPosition) {
                rtnArray.add("");
            }
        }
        return rtnArray;
    }


    /**
     * 计算指定文字的个数。
     *
     * @param str   文字列
     * @param c     文字
     * @param start 开始位置
     * @return 个数
     */
    private int countChar(String str, char c, int start) {
        int i = 0;
        int index = str.indexOf(c, start);
        return index == -1 ? i : countChar(str, c, index + 1) + 1;
    }

    /**
     * 查询下一个逗号的位置。
     *
     * @param source 文字列
     * @param st     检索开始位置
     * @return 下一个逗号的位置。
     */
    private static int nextComma(String source, int st) {
        int maxPosition = source.length();
        boolean inquote = false;
        while (st < maxPosition) {
            char ch = source.charAt(st);
            if (!inquote && ch == ',') {
                break;
            } else if ('"' == ch) {
                inquote = !inquote;
            }
            st++;
        }
        return st;
    }

    /**
     * 取得下一个字符串
     */
    private static String nextToken(String source, int st, int nextComma) {
        StringBuffer sb = new StringBuffer();
        int next = st;
        while (next < nextComma) {
            char ch = source.charAt(next++);
            if (ch == '"') {
                if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {
                    sb.append(ch);
                    next++;
                }
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
