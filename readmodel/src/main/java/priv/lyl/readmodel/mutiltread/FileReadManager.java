package priv.lyl.readmodel.mutiltread;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import priv.lyl.readmodel.model.FileResource;

public class FileReadManager {
    //请求线程队列
    private LinkedList<ReadFileTask> mReadFileTasks;
    //任务不能重复
    private Set<FileResource> mTaskIdSet;
    //单例
    private static FileReadManager instance;

    public static synchronized FileReadManager getInstance() {
        if (null == instance) {
            instance = new FileReadManager();
        }
        return instance;
    }

    private FileReadManager() {
        mReadFileTasks = new LinkedList<ReadFileTask>();
        mTaskIdSet = new HashSet<FileResource>();
    }

    /**
     * 向队列最后添加任务
     *
     * @param readFileTask
     */
    public void addReadFileTask(ReadFileTask readFileTask) {
        synchronized (mReadFileTasks) {
            if (!isTaskRepeat(readFileTask.getFileResource())) {
                switch (readFileTask.getFileResource().getIndex()) {
                    case FileResource.FRIST:
                        mReadFileTasks.addFirst(readFileTask);
                        break;
                    case FileResource.END:
                    default:
                        mReadFileTasks.addLast(readFileTask);
                }
            }
        }
    }

    /**
     * 在队列中移除任务
     *
     * @param readFileTask
     */
    public void removeReadFileTask(ReadFileTask readFileTask) {
        synchronized (mReadFileTasks) {
            if (!isTaskRepeatRemove(readFileTask.getFileResource())) {
                mReadFileTasks.remove(readFileTask);
            }
        }
    }

    /**
     * 移除所有任务队列
     */
    public void removeAll() {
        synchronized (mReadFileTasks) {
            mReadFileTasks.clear();
        }
    }

    /**
     * 添加任务列表
     *
     * @param readFileTasks
     */
    public void addReadFileTasks(List<ReadFileTask> readFileTasks) {
        for (ReadFileTask rft : readFileTasks) {
            addReadFileTask(rft);
        }

    }

    /**
     * 如果重复则删除
     *
     * @param fileResource
     * @return
     */
    public boolean isTaskRepeatRemove(FileResource fileResource) {
        synchronized (mTaskIdSet) {
            if (mTaskIdSet.contains(fileResource)) {
                mTaskIdSet.remove(fileResource);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isTaskRepeat(FileResource fileResource) {
        synchronized (mTaskIdSet) {
            if (mTaskIdSet.contains(fileResource)) {
                return true;
            } else {
                System.out.println("下载管理器增加读取任务：" + fileResource);
                mTaskIdSet.add(fileResource);
                return false;
            }
        }
    }

    /**
     * 从队列中获取第一个任务
     *
     * @return
     */
    public ReadFileTask getReadFileTask() {
        synchronized (mReadFileTasks) {
            if (mReadFileTasks.size() > 0) {
                ReadFileTask ReadFileTask = mReadFileTasks.removeFirst();
                return ReadFileTask;
            }
        }
        return null;
    }
}
