package com.hom.wien.tu.Utilities;

/**
 * Created by davorsafranko on 11/9/17.
 */

public class PageEntry {
    public int a, b;
    public int page;

    public PageEntry(int a, int b, int page) {
        this.a = a;
        this.b = b;
        this.page = page;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof PageEntry)) return false;

        PageEntry other = (PageEntry)obj;
        return other.a == this.a && other.b == this.b && other.page == this.page;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "Edge (" + this.a + ", " + this.b + ") on page " + this.page;
    }
}