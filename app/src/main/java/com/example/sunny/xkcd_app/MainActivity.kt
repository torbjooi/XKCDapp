package com.example.sunny.xkcd_app

import android.view.View;
import android.view.View.OnClickListener;


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.text.Editable
import android.widget.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.EditText


class MainActivity : AppCompatActivity() {

    private var index = 1 // Index decides what comic should be displayed
    private var lastComicNr = 0 // Helpfull for stopping index becoming higher then the max nr(stopping 404 errors)
    // Also helpful when checks for new comics gets implemented.
    private var editText: EditText? = null; // Used for searching by comicNr and displaying current comicNr

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText) as EditText

        val getURL = GetWebAPI()
        lastComicNr = Integer.parseInt(getURL.execute(-1).get())

        displayLast()


    }


    fun displayComic(url: String) {
        // Takes an urlString and displays it to imageView using Glide.


        val imageView = findViewById<View>(R.id.imageView) as ImageView


        Glide.with(this)
            .load(url)
            .override(500,500)
            .into(imageView)
    }


    fun buttonPressNext(view: View) {

        //called when the next button is pressed.
        //increases current index by 1
        //Gets an URL based on current index.
        //Displays the URL as an image

        if (index == lastComicNr)return; // Makes sure nothing happens when pressing the button when on the last comic

        var url: String = "";

        val getURL = GetWebAPI()
        url = getURL.execute(index).get()
        if(index == 403)index++; // comicnr 404 returns a 404 error this check will send index from 403->405
        index++
        editText?.setText(Integer.toString(index))

        displayComic(url)

        Toast.makeText(applicationContext, lastComicNr.toString(), Toast.LENGTH_SHORT).show()

    }

    fun buttonPressPrevious(view: View) {
        //called when the previous button is pressed.
        //decreases current index by 1
        //Gets an URL based on current index.
        //Displays the URL as an image

        if (index <= 1) return; // Makes sure you cant press the button when it is at the first comic or for some reason(This should not be possilbe) lower

        var url: String = "";
        val getURL = GetWebAPI()
        url = getURL.execute(index).get()
        if(index==405)index--; // comicnr 404 returns a 404 error this check will send index from 405->403
        index--
        editText?.setText(Integer.toString(index))


        displayComic(url)
        Toast.makeText(applicationContext, index.toString(), Toast.LENGTH_SHORT).show()

    }

    fun buttonPressSearch(view: View) {
        //Gets called when the lower search button is pressed.
        //Search by the number entered in the editView next to it and gets an URL from the nr.
        // Displays the comic by that URL


        index = Integer.parseInt(editText?.text.toString())

        if (index > lastComicNr || index == 0 || index == 404) return

        var url: String = "";
        val getURL = GetWebAPI()
        url = getURL.execute(index).get()
        displayComic(url)

    }

    fun buttonPressSearchByString(view: View) {
        // Gets called when the upper search button is pressed.
        // Takes the search string from the upper editText and gets the Index of the most relevant comic.
        // Takes the index and gets the URL to the Comic.
        // Displays the comic.

        val searchBox = findViewById(R.id.editText2) as EditText
        val searchString = searchBox.text.toString()
        val getUrlByString = GetUrlByString()
        index = getUrlByString.execute(searchString).get()
        val getURL = GetWebAPI()
        val url = getURL.execute(index).get()
        editText?.setText(Integer.toString(index))
        displayComic(url)


    }

    fun displayLast(){ // Displays the last comic.
        val getURL = GetWebAPI()
        val url = getURL.execute(lastComicNr).get()
        index = lastComicNr
        displayComic(url)

    }


}