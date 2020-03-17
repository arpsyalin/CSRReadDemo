package priv.lyl.csvread.service;

import java.util.List;

import priv.lyl.readmodel.model.FileResource;

public interface IReadFileService {
    void put(FileResource item);

    void putAll(List<FileResource> items);

    void remove(FileResource fileResource);

    void removeAll();
}
