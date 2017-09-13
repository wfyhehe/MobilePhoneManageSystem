package com.wfy.web.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public class VCode implements Serializable {
    private String id;
    private String credentials;

    public VCode() {
    }

    public VCode(String id, String credentials) {
        this.id = id;
        this.credentials = credentials;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VCode vCode = (VCode) o;

        return id.equals(vCode.id) && credentials.equalsIgnoreCase(vCode.credentials);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + credentials.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VCode{" +
                "id='" + id + '\'' +
                ", credentials='" + credentials + '\'' +
                '}';
    }
}
