package com.yt.jetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yt.jetpackdemo.persistence.User
import com.yt.jetpackdemo.ui.UserViewModel
import com.yt.jetpackdemo.ui.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    private var adapter: TestAdapter? = null

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        initUI()
    }

    /**
     * 初始化
     */
    private fun initUI() {
        btn_refresh.setOnClickListener { queryUser() }
        btn_insert.setOnClickListener { insertUser() }

        adapter = TestAdapter(android.R.layout.simple_expandable_list_item_2, null)
        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val user: User = adapter.getItem(position) as User
//            deleteUser(user)
            updateUser(user)
        }
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }

    /**
     * 插入记录
     */
    private fun insertUser() {
        btn_insert.isEnabled = false
        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
        val id = UUID.randomUUID().toString()
        val user = User(id, "name:$id",Date())
        disposable.add(
            viewModel.insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { btn_insert.isEnabled = true },
                    { error -> Log.e(TAG, "Unable to update username", error) })
        )
    }

    /**
     * 查询
     */
    private fun queryUser() {
        btn_refresh.isEnabled = false
        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
        disposable.add(
            viewModel.userCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    btn_refresh.isEnabled = true
                    tv_db.text = "总记录:$it"
                },
                    { error -> Log.e(TAG, "Unable to update username", error) })
        )
        disposable.add(
            viewModel.queryAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (adapter != null) {
                        adapter!!.setNewData(it)
                    }
                },
                    { error -> Log.e(TAG, "Unable to update username", error) })
        )
    }

    /**
     * 删除
     */
    private fun deleteUser(user: User) {
        disposable.add(
            viewModel.delete(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error -> Log.e(TAG, "dd", error) })
        )
    }

    private fun updateUser(user: User) {
        user.userName = "NNNNNN"
        disposable.add(
            viewModel.update(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error -> Log.e(TAG, "dd", error) })

        )
    }

    override fun onStart() {
        super.onStart()
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        disposable.add(
            viewModel.userName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.tv_db.text = it },
                    { error -> Log.e(TAG, "Unable to get username", error) })
        )
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }

    private fun updateUserName() {
        val userName = user_name_input.text.toString()
        // Disable the update button until the user name update has been done
        btn_insert.isEnabled = false
        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
        disposable.add(
            viewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ btn_insert.isEnabled = true },
                    { error -> Log.e(TAG, "Unable to update username", error) })
        )
    }

//    private fun showPop() {
//        val view = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, null)
//        view.setBackgroundColor(Color.BLACK)
//        val textView = view.findViewById<TextView>(android.R.id.text1)
//        textView.setTextColor(Color.WHITE)
//        textView.text = "HHHHH"
//        val pop =
//            PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
//        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//        pop.showAtLocation(
//            btn_refresh,
//            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
//            , 0, 0
//        )
//
//    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
