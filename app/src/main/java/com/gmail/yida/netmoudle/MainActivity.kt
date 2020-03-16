package com.gmail.yida.netmoudle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getPoetryByTag()
        viewModel.list.observe(this, Observer {
            var iterator = it.iterator()
            while (iterator.hasNext()) {
                Log.e("text", iterator.next().content)
            }
            text.text = it.toString()
        })
    }
}
