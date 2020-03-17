package priv.lyl.readmodel.factory;


import android.content.Intent;

import priv.lyl.readmodel.analysis.CsvFileAnalysisImpl;
import priv.lyl.readmodel.distribute.ResultDistributeImpl;
import priv.lyl.readmodel.interf.IFileAnalysis;
import priv.lyl.readmodel.interf.IFileRead;
import priv.lyl.readmodel.interf.IReadFileFactory;
import priv.lyl.readmodel.interf.IResultDistribute;
import priv.lyl.readmodel.model.FileResource;
import priv.lyl.readmodel.read.CSVReadImpl;

public class ReadFileFactoryImpl implements IReadFileFactory {
    private static ReadFileFactoryImpl instance;

    public static synchronized ReadFileFactoryImpl getInstance() {
        if (null == instance) {
            instance = new ReadFileFactoryImpl();
        }
        return instance;
    }

    @Override
    public IFileRead getFileRead(FileResource model) {
        try {
            return new CSVReadImpl(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IFileAnalysis getFileAnalysis(FileResource model) {
        return new CsvFileAnalysisImpl();
    }

    @Override
    public IResultDistribute getResultDistribute(FileResource model) {
        return new ResultDistributeImpl();
    }
}
