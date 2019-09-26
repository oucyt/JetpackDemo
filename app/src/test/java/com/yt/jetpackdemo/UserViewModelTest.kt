package com.yt.jetpackdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yt.jetpackdemo.persistence.BreakfastTicketDao
import com.yt.jetpackdemo.persistence.User
import com.yt.jetpackdemo.persistence.UserDao
import com.yt.jetpackdemo.ui.UserViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.12 20:45
 * @since 1.0.0
 */

/**
 * Unit test for [UserViewModel]
 */
class UserViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockitoAnnotations.Mock
    private lateinit var dataSource: UserDao

    @MockitoAnnotations.Mock
    private lateinit var ticketSource: BreakfastTicketDao

    @Captor
    private lateinit var userArgumentCaptor: ArgumentCaptor<User>

    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = UserViewModel(dataSource,ticketSource)
    }

    /**
     * 用例：empty table 获取username
     */
    @Test
    fun getUserName_whenNoUserSaved() {
        // Given that the UserDataSource returns an empty list of users
        `when`(dataSource.getUserById(UserViewModel.USER_ID)).thenReturn(Flowable.empty<User>())

        //When getting the user name
        viewModel.userName()
            .test()
            // The user name is empty
            .assertNoValues()
    }

    /**
     * 用例：查询
     */
    @Test
    fun getUserName_whenUserSaved() {
        // Given that the UserDataSource returns a user
        val user = User(userName = "user name", updateTime = Date())
        `when`(dataSource.getUserById(UserViewModel.USER_ID)).thenReturn(Flowable.just(user))

        //When getting the user name
        viewModel.userName()
            .test()
            // The correct user name is emitted
            .assertValue("user name")
    }

    @Test
    fun updateUserName_updatesNameInDataSource() {
        // Given that a user is already inserted
        dataSource.insert(User(UserViewModel.USER_ID, "name",Date()))

        // And a specific user is expected when inserting
        val userName = "new user name"
        val expectedUser = User(UserViewModel.USER_ID, userName, Date())
        `when`(dataSource.insert(expectedUser)).thenReturn(Completable.complete())

        // When updating the user name
        viewModel.updateUserName(userName)
            .test()
            .assertComplete()
    }

}
