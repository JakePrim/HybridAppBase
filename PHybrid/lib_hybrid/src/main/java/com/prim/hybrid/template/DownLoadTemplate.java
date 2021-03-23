package com.prim.hybrid.template;

import android.renderscript.ScriptGroup;

import com.prim.hybrid.entry.DownloadEntry;

import java.io.File;
import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 11:52 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public interface DownLoadTemplate {
    void download(DownloadEntry entry);
}
