package com.thomaskioko.daraja.db

import android.support.test.runner.AndroidJUnit4
import com.thomaskioko.daraja.util.LiveDataTestUtil.getValue
import com.thomaskioko.daraja.util.TestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.IsNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SafaricomTokenDaoTest : SafaricomDbTest() {


    @Test
    fun insert_SafaricomTokenDao_ShouldLoadInsertedRecord() {

        val safaricomToken = TestUtil.createToken()
        db.safaricomTokenDao().insertSafaricomToken(safaricomToken)

        val loadedToken = getValue(db.safaricomTokenDao().getAccessToken())

        //Verify that data from db is same as data created
        assertThat(loadedToken.accessToken, `is`(safaricomToken.accessToken))
    }

    @Test
    fun update_SafaricomTokenDao_ShouldLoadUpdatedRecord() {

        val safaricomToken = TestUtil.createToken()
        db.safaricomTokenDao().insertSafaricomToken(safaricomToken)

        val updatedSafaricomToken = TestUtil.createToken("vK13flUwxNhIsYPzvNAJwslDnruQE")
        db.safaricomTokenDao().updateSafaricomToken(updatedSafaricomToken)

        val loadedToken = getValue(db.safaricomTokenDao().getAccessToken())

        //Verify that data from db is same as data updated
        assertThat(loadedToken.accessToken, `is`(updatedSafaricomToken.accessToken))
    }

    @Test
    fun delete_SafaricomTokenDao_ShouldNotReturnData(){

        val safaricomToken = TestUtil.createToken()
        db.safaricomTokenDao().insertSafaricomToken(safaricomToken)


        val loadedToken = getValue(db.safaricomTokenDao().getAccessToken())

        //Verify that data from db is same as data created
        assertThat(loadedToken.accessToken, `is`(safaricomToken.accessToken))


        db.safaricomTokenDao().deleteAll()

        val result = getValue(db.safaricomTokenDao().getAccessToken())
        assertThat(result, `is`(IsNull.nullValue()))

    }

    @Test
    fun timDateIsNotNul() {

        //Given that data is created and inserted
        val safaricomToken = TestUtil.createToken()
        db.safaricomTokenDao().insertSafaricomToken(safaricomToken)

        //Check that offsetTime is not null
        assertThat(safaricomToken.expireTime, `is`(notNullValue()))
    }
}

