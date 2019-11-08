package com.yt.jetpackdemo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amitshekhar.DebugDB
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yt.jetpackdemo.persistence.User
import com.yt.jetpackdemo.ui.UserViewModel
import com.yt.jetpackdemo.ui.ViewModelFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    private var adapter: TestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        initUI()
        queryUser()
        Log.e(TAG, "数据库调试地址：" + DebugDB.getAddressLog())
    }

    /**
     * 初始化
     */
    private fun initUI() {
        btn_refresh.setOnClickListener { queryUser() }
        btn_insert.setOnClickListener { insertUser() }
        adapter = TestAdapter(R.layout.item_user, null)

        adapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val user: User = adapter.getItem(position) as User
            when (view.id) {
                R.id.btn_update -> {
                    var etName = adapter.getViewByPosition(position, R.id.et_name) as EditText;
                    var etAge = adapter.getViewByPosition(position, R.id.et_age) as EditText;
                    user.userName = etName.text.toString()
                    user.age = Integer.parseInt(etAge.text.toString())
                    updateUser(user)
                }
                R.id.btn_delete -> deleteUser(user)
            }
        }
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
        adapter!!.bindToRecyclerView(recycler_view)
    }

    /**
     * 插入记录
     */
    private fun insertUser() {
        if (et_input.text.isNullOrEmpty())
            return

        btn_insert.isEnabled = false
        val id = UUID.randomUUID().toString()
        val user = User(id, et_input.text.toString(), Random().nextInt(99))

//        viewModel.insert(user)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    btn_insert.isEnabled = true
//                    queryUser()
//                },
//                { error ->
//                    //                    Log.e(TAG, "Unable to update username", error)
//                    tv_console.text = "异常：${error.message}"
//                })


        Flowable.fromPublisher<Long> {
            it.onNext(viewModel.insert2(user))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    btn_insert.isEnabled = true
                    tv_console.text = "返回值：${it}"
                    queryUser()
                },
                { error ->
                    btn_insert.isEnabled = true
                    tv_console.text = "异常：${error.message}"
                })
    }

    /**
     * 查询
     */
    private fun queryUser() {
        viewModel.queryAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (adapter != null) {
                    adapter!!.setNewData(it)
                }
            },
                { error ->
                    Log.e(TAG, "Unable to update username", error)
                })
    }


    private fun updateUser(user: User) {
//        viewModel.update(user)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({}, { error -> Log.e(TAG, "dd", error) })
        var newUser = user.copy(id = "999")
        Flowable.fromPublisher<Int> {
            it.onNext(viewModel.update2(newUser))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    tv_console.text = "影响记录条数：${it}"
                    queryUser()
                },
                { error ->
                    tv_console.text = "异常：${error.message}"
                })
    }


    /**
     * 删除
     */
    private fun deleteUser(user: User) {
//        viewModel.delete(user)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({}, { error -> Log.e(TAG, "dd", error) })

        Flowable.fromPublisher<Int> {
            it.onNext(viewModel.delete2(user))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    tv_console.text = "影响记录条数：${it}"
                    queryUser()
                },
                { error ->
                    tv_console.text = "异常：${error.message}"
                })
    }

    override fun onStart() {
        super.onStart()
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
//        viewModel.userName()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ this.tv_db.text = it },
//                { error -> Log.e(TAG, "Unable to get username", error) })
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
    }

    private fun updateUserName() {
        val userName = et_input.text.toString()
        // Disable the update button until the user name update has been done
        btn_insert.isEnabled = false
        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
//        viewModel.updateUserName(userName)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ btn_insert.isEnabled = true },
//                { error -> Log.e(TAG, "Unable to update username", error) })
    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
