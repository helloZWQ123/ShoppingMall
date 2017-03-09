package com.atguigu.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiXin on 2017/2/27.
 */

public class CartStorage {
    private static CartStorage instance;
    private static Context mContext;
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage() {

        sparseArray = new SparseArray<>();
        //从本地获取数据
        listToSparseArray();
    }

    private void listToSparseArray() {
        List<GoodsBean> beanList = getAllData();
        for (int i = 0; i < beanList.size(); i++) {
            GoodsBean goodsbean = beanList.get(i);
            sparseArray.put(Integer.parseInt(goodsbean.getProduct_id()), goodsbean);
        }
    }

    public List<GoodsBean> getAllData() {

        return getLocalData();
    }

    private List<GoodsBean> getLocalData() {
        List<GoodsBean> goodsBeen = new ArrayList<>();
        String json = CacheUtils.getString(mContext, "json_cart");
        if (!TextUtils.isEmpty(json)) {
            //转化成list
            goodsBeen = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeen;
    }

    public static CartStorage getInstance(Context context) {
        if (mContext == null) mContext = context;

        return CartStorageTool.cartStorage;
    }

    static class CartStorageTool {
        public static CartStorage cartStorage = new CartStorage();
    }

    public void addData(GoodsBean goodsbean) {
        //从sparseArray中获取数据
        GoodsBean tempGoodsBean = sparseArray.get(Integer.parseInt(goodsbean.getProduct_id()));
        if (tempGoodsBean != null) {
            tempGoodsBean.setNumber(tempGoodsBean.getNumber() + goodsbean.getNumber());
        } else {
            tempGoodsBean = goodsbean;
        }
        //添加到集合中
        sparseArray.put(Integer.parseInt(tempGoodsBean.getProduct_id()), tempGoodsBean);
        saveLocal();
    }

    public void deleteData(GoodsBean goodsBean) {
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        saveLocal();
    }

    public void upDateData(GoodsBean goodsBean) {
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        saveLocal();
    }


    private void saveLocal() {
        //sparseArray转换成list
        List<GoodsBean> goodsBeanList = sparseArrayToList();
        //list转换成json的String类型
        String json = new Gson().toJson(goodsBeanList);
        //保存
        CacheUtils.setString(mContext, "json_cart", json);
    }

    private List<GoodsBean> sparseArrayToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            goodsBeanList.add(sparseArray.valueAt(i));
        }
        return goodsBeanList;
    }
}
