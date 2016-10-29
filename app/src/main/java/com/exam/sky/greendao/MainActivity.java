package com.exam.sky.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import com.exam.sky.greendao.bean.Product;
import com.exam.sky.greendao.bean.ProductDao;
import com.exam.sky.greendao.callback.OnItemClickListener;
import com.exam.sky.greendao.callback.OnItemLongClickListener;
import com.exam.sky.greendao.db.DBManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 1.插入：单挑插入
 * <p>
 * <p>
 * <p>
 * 备注：SQLiteDataBase:1>继承SQliteOpenHelper,2>OpenOrCreateDataBase
 * 主键id自动增长，但是一旦原有数据删除，下次再添加新数据id还是从上一下开始自增
 * <p>
 * <p>
 * <p>
 * greenDao:主键id不能自动增长，自己指定；模拟是网络数据；
 * <p>
 * <p>
 * 2.查询：查询所有，按条件查询
 * 3.删除：单条删除，批量删除；
 * 4.更新：数量更新
 */

public class MainActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener {

    ProductDao productDao;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void initView() {
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.reyclerView);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //默认加载空列表
        myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);

        //设置点击事件
        myAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int pos) {
                Log.e("=====","===setOnItemClickListener====");
                //一般进入详情；插入
                Product p = new Product(pos, "哈哈", 20);
                DBManager.getDBInstance(MainActivity.this).insert(p);
                queryData(null);
            }
        });

        myAdapter.setmOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void setOnItemLongClickListener(int pos, Product product) {
                DBManager.getDBInstance(MainActivity.this).delete(product);
                queryData(null);

            }
        });
    }

    public void insert(View view) {
        //批量插入，一般用作缓存
        List<Product> products = new ArrayList<>();
        Product p = new Product(0, "absc", 20);
        products.add(p);

        Product p2 = new Product(1, "bbbbb", 20);
        products.add(p2);

        Product p3 = new Product(2, "aabbbb", 20);
        products.add(p3);
        Product p4 = new Product(3, "csfdsfd", 20);
        products.add(p4);

        DBManager.getDBInstance(this).insertAll(products);
        queryData(null);

    }

    public void query(View view) {
        //查询所有
        queryData(null);
    }

    public void delete(View view) {
        //DBManager.getDBInstance(this).deleteAll();


        //删除单挑
        Product p4 = new Product(3, "csfdsfd", 20);
        DBManager.getDBInstance(this).delete(p4);
        queryData(null);
    }

    public void update(View view) {
        Product p2 = new Product(1, "mkmkm", 546);
        DBManager.getDBInstance(this).update(p2);
        queryData(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        queryData(newText);
        return false;
    }

    //刷新列表
    public void refresh(List<Product> products) {
        myAdapter.setProductList(products);
        //刷新列表
        myAdapter.notifyDataSetChanged();
    }

    //查询刷新列表
    public void queryData(String key) {
        List<Product> products = new DBManager(this).queryAll(key);
        refresh(products);
    }

}
