package com.exam.sky.greendao.db;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.exam.sky.greendao.bean.DaoMaster;
import com.exam.sky.greendao.bean.DaoSession;
import com.exam.sky.greendao.bean.Product;
import com.exam.sky.greendao.bean.ProductDao;
import com.exam.sky.greendao.bean.StudentDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by bluesky on 16/10/19.
 */

public class DBManager {
    ProductDao productDao;
    private    static DBManager dbManager = null;

    public static DBManager getDBInstance(Context ctx) {
        if (dbManager == null) {
            dbManager = new DBManager(ctx);
        }
        return dbManager;
    }


    public DBManager(Context ctx) {
        //参数2:数据库的名称
        DaoSession daoSession = DaoMaster.newDevSession(ctx, "1019");
        //获取实体类对应的表
        StudentDao studentDao = daoSession.getStudentDao();
        productDao = daoSession.getProductDao();
    }

    public void insert(Product p) {
        //单条插入
        productDao.insertOrReplace(p);
    }
    public void insertAll(List<Product> list) {
        //可以插入多次，但是相同的数据只是替换新值；没有的数据也就是不同数据插入
        productDao.insertOrReplaceInTx(list);
    }
    public void queryAll() {
        //参数1不能为nullm,基本查询
//        List<Product> list =  productDao.queryRaw("",new String[]{});
//        for (int i = 0; i < list.size(); i++) {
//            Log.e("=====","===getId==="+list.get(i).getId());
//            Log.e("=====","==getName===="+list.get(i).getName());
//            Log.e("=====","===getPrice==="+list.get(i).getPrice());
//        }

        //按条件查询QueryBuilder，建造者模式
        QueryBuilder<Product> builder = productDao.queryBuilder();
        Query<Product> query = builder.build();
        List<Product> list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Log.e("=====", "===getId===" + list.get(i).getId());
            Log.e("=====", "==getName====" + list.get(i).getName());
            Log.e("=====", "===getPrice===" + list.get(i).getPrice());
        }

    }
    //分页
    public void queryAll(int offset, int limit) {
        //模拟分页
        //offset偏移量，limit()限制查询的条数，
        //offset(m).limit(n)从第m个数开始查询，查询n条数据
        QueryBuilder<Product> builder = productDao.queryBuilder();
        Query<Product> query = builder.offset(10).limit(10).build();
        List<Product> list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Log.e("=====", "===getId===" + list.get(i).getId());
            Log.e("=====", "==getName====" + list.get(i).getName());
            Log.e("=====", "===getPrice===" + list.get(i).getPrice());
        }

    }

    //模糊查询
    public List<Product> queryAll(String key) {
        //模糊查询 "where name  like %N%"
        QueryBuilder<Product> builder = productDao.queryBuilder();
        Query<Product> query = null;

        if (!TextUtils.isEmpty(key)) {
            //按条件查询
            query = builder
                    .where(ProductDao.Properties.Name.like("%" + key + "%"))
                    .build();
        } else {
            //查询所有
            query = builder.build();
        }
        List<Product> list = query.list();
        return list;
    }


    //删除单条数据
    public  void delete(Product p){
        productDao.delete(p);
    }
    //删除所有
    public  void deleteAll(){
        productDao.deleteAll();
    }

    public void update(Product p){
        productDao.update(p);
    }
}
