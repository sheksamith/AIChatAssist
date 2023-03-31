package com.samith.chatgptplus.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Data class representing a chat message.
 *
 * @property sender the ID of the sender (e.g. the user or the bot)
 * @property type the type of message (e.g. text or image)
 * @property message the content of the message
 */
data class Chat (
    var sender: Int,
    var type: Int,
    var message: String,
) : Parcelable {
    // parcelable implementation

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(sender)
        dest.writeInt(type)
        dest.writeString(message)
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}
