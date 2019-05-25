package com.github.alexdochioiu.main_feature_repository

import com.github.alexdochioiu.main_feature_common_objects.Cake
import com.github.alexdochioiu.main_feature_networking.retrofit.CakesService
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CakesRepositoryTest {

    private val firstCake: Cake = Cake("Cheesecake", "description", "cheesecake_url")
    private val firstCakeCopy: Cake = firstCake.copy()
    private val secondCake: Cake = Cake("Pie", "pie desc", "pie url")
    private val thirdCake: Cake = Cake("Carrot Cake", "desc", "cakeUrl")
    private val fourthCake: Cake = Cake("Lemon Cake", "lemon cake desc", "lemon cakeUrl")


    @Mock
    lateinit var cakesService: CakesService
    lateinit var cakesRepository: CakesRepository

    @Before
    fun setUp() {
        cakesRepository = CakesRepository(cakesService)
    }

    @Test
    fun `test assumption`() {
        // our tests rely on this assumption
        assertEquals(firstCake, firstCakeCopy)
        assertNotSame(firstCake, firstCakeCopy)
    }

    @Test
    fun `getUniqueOrderedCakes works for empty response`() {
        val input = emptyList<Cake>()
        Mockito.doReturn(Single.just(input)).`when`(cakesService).getAllCakes()

        val expected = emptyArray<Cake>()
        val returned = cakesRepository.getUniqueOrderedCakes().blockingGet().toTypedArray()

        assertArrayEquals(expected, returned)
    }

    @Test
    fun `getUniqueOrderedCakes works for single entry`() {
        val input = listOf(firstCake)
        Mockito.doReturn(Single.just(input)).`when`(cakesService).getAllCakes()

        val expected = listOf(firstCake).toTypedArray()
        val returned = cakesRepository.getUniqueOrderedCakes().blockingGet().toTypedArray()

        assertArrayEquals(expected, returned)
    }

    @Test
    fun `getUniqueOrderedCakes removes all duplicate entries`() {
        val input = listOf(firstCake, firstCakeCopy, firstCake)
        Mockito.doReturn(Single.just(input)).`when`(cakesService).getAllCakes()

        val expected = listOf(firstCake).toTypedArray()
        val returned = cakesRepository.getUniqueOrderedCakes().blockingGet().toTypedArray()

        assertArrayEquals(expected, returned)
    }

    @Test
    fun `getUniqueOrderedCakes orders entries alphabetically by name`() {
        val input = listOf(firstCake, secondCake, thirdCake, fourthCake)
        Mockito.doReturn(Single.just(input)).`when`(cakesService).getAllCakes()

        val expected = listOf(thirdCake, firstCake, fourthCake, secondCake).toTypedArray()
        val returned = cakesRepository.getUniqueOrderedCakes().blockingGet().toTypedArray()

        assertArrayEquals(expected, returned)
    }

    @Test
    fun `getUniqueOrderedCakes removes duplicate entries and orders remaining alphabetically by name`() {
        val input = listOf(firstCake, secondCake, firstCakeCopy, thirdCake, secondCake, fourthCake, firstCake, firstCakeCopy)
        Mockito.doReturn(Single.just(input)).`when`(cakesService).getAllCakes()

        val expected = listOf(thirdCake, firstCake, fourthCake, secondCake).toTypedArray()
        val returned = cakesRepository.getUniqueOrderedCakes().blockingGet().toTypedArray()

        assertArrayEquals(expected, returned)
    }
}