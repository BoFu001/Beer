package com.bofu.coroutinesretrofitmvvm

import com.bofu.coroutinesretrofitmvvm.extensions.getYear
import com.bofu.coroutinesretrofitmvvm.models.Beer
import com.bofu.coroutinesretrofitmvvm.models.YearFilter
import com.bofu.coroutinesretrofitmvvm.services.BeerService
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FilterYearTest {

    private lateinit var beerService: BeerService
    private val beerArray = mutableListOf<Beer>()

    @Before
    fun init(){
        beerService = BeerService()

        runBlocking {
            beerService.getBeers().collect { beerArray.add(it) }
        }
    }

    @Test
    fun test_valid_year_in_service() {
        for (item in beerArray) {
            TestCase.assertTrue("Year is valid between 2000 and 2030", item.year in 2000..2030)
        }
    }


    @Test
    fun test_year_format() {
        val y0 = beerArray[0].getYear()
        Truth.assertThat(y0).isEqualTo(2007)

        val y1 = beerArray[1].getYear()
        Truth.assertThat(y1).isEqualTo(2008)

        val y2 = beerArray[2].getYear()
        Truth.assertThat(y2).isEqualTo(2015)

        val y3 = beerArray[3].getYear()
        Truth.assertThat(y3).isEqualTo(2013)
    }

    @Test
    fun test_filter() {

        val filter = YearFilter(2006,2007)

        val filteredItems = beerArray.filter {
            it.year >= filter.start && it.year <= filter.end
        }

        Truth.assertThat(filteredItems.size).isEqualTo(2)
    }
}