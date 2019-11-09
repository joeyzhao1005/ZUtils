package com.kit.utils.bitmap;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PatchInputStream extends FilterInputStream {

    protected PatchInputStream(InputStream in) {
      super(in);
      // TODO Auto-generated constructor stub
    }

    @Override
    public long skip(long n)throws IOException {
      long m=0L;
      while(m<n){
        long _m=in.skip(n-m);
        if(_m==0L){
          break;
        }
        m+=_m;
      }
      return m;
    }
  }