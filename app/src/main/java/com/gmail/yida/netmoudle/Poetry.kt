package com.gmail.yida.netmoudle

import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.text.Spanned
import com.gmail.yida.retrofitmoudle.common.IBaseResponse

class Poetry() : Parcelable, IBaseResponse<List<Poetry>?> {
    override fun msg(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun data(): List<Poetry>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var content: String? = null
    var translate: String? = null
    var notes: String? = null
    var appreciation: String? = null
    var pinyin: String? = null
    var name: String? = null
    var dynasty: String? = null
    var poet: String? = null
    var poetId: Long = 0
    var tag: String? = null
    var isTag: Int = 0

    constructor(parcel: Parcel) : this() {
        content = parcel.readString()
        translate = parcel.readString()
        notes = parcel.readString()
        appreciation = parcel.readString()
        pinyin = parcel.readString()
        name = parcel.readString()
        dynasty = parcel.readString()
        poet = parcel.readString()
        poetId = parcel.readLong()
        tag = parcel.readString()
        isTag = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
        parcel.writeString(translate)
        parcel.writeString(notes)
        parcel.writeString(appreciation)
        parcel.writeString(pinyin)
        parcel.writeString(name)
        parcel.writeString(dynasty)
        parcel.writeString(poet)
        parcel.writeLong(poetId)
        parcel.writeString(tag)
        parcel.writeInt(isTag)
    }

    fun getText(): Spanned {
        return Html.fromHtml(content?.replace("</p> <p>", "<br>")!!
                .replace("，<br>", "，")
                .replace("，", "，<br>")
                .replace("；", "；<br>")
                .replace("？", "，<br>")
                .replace("！", "，<br>")
                .replace("。&nbsp", "。")
                .replace("。&nbsp;", "。")
                .replace("。&nbsp ;", "。")
                .replace("。 <br/>", "。")
                .replace("。 <br>", "。")
                .replace("。<br/>", "。")
                .replace("。<br>", "。")
                .replace("。", "。<br>")
                .replace("。<br>;", "。<br>")
                .replace("<br> <br>", "<br>")
                .replace("<br><br/>", "<br>")
                .replace("<br><br>", "<br>")
                .replace("<br></span><br/>", "<br>"))
    }

    fun getTitle(): Spanned {
        return Html.fromHtml(name?.replace("</p> <p>", "<br>"))
    }

    fun getTrans(): Spanned {
        return Html.fromHtml(translate?.replace("</p> <p>", "<br>"))
    }

    fun getnotes(): Spanned {
        return Html.fromHtml(notes?.replace("</p> <p>", "<br>"))
    }

    fun getApprecate(): Spanned {
        return Html.fromHtml(appreciation?.replace("</p> <p>", "<br>"))
    }

    fun getPoetName(): Spanned {
        return Html.fromHtml(poet?.replace("</p> <p>", "<br>"))
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Poetry(content=$content)"
    }

    companion object CREATOR : Parcelable.Creator<Poetry> {
        override fun createFromParcel(parcel: Parcel): Poetry {
            return Poetry(parcel)
        }

        override fun newArray(size: Int): Array<Poetry?> {
            return arrayOfNulls(size)
        }
    }
}