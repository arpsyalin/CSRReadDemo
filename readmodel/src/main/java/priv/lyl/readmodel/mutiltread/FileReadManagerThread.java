package priv.lyl.readmodel.mutiltread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池管理运行子线程
 */
public class FileReadManagerThread implements Runnable {
    FileReadManager mFileReadManager;
    // 创建一个可重用固定线程数的线程池
    private ExecutorService pool;
    // 线程池大小
    private final int POOL_SIZE = 5;
    // 轮询时间
    private final int SLEEP_TIME = 1000;
    // 是否停止
    private boolean isStop = false;

    public FileReadManagerThread() {
        this.mFileReadManager = FileReadManager.getInstance();
        pool = Executors.newFixedThreadPool(POOL_SIZE);


    }

    @Override
    public void run() {
        /**
         * 如果没有停止则一直遍历
         */
        while (!isStop) {
            ReadFileTask readFileTask = mFileReadManager.getReadFileTask();
            if (readFileTask != null) {
                pool.execute(readFileTask);
            } else {
                try {
                     Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (isStop) {
            pool.shutdown();
        }
    }

    /**
     * 停止
     *
     * @param isStop
     */
    public void setStop(boolean isStop) {
        this.isStop = isStop;
    }
}

