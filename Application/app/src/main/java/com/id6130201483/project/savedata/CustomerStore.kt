package com.id6130201483.project.savedata

interface CustomerStore {
    companion object {
        var id: Int? = 0
        var username: String? = ""
        var email: String? = ""
        var fname: String? = ""
        var lname: String? = ""
        var address: String? = ""
        var tel: String? = ""
        var imageURL: String? = ""

        fun setData(
            id: Int,
            username: String,
            email: String,
            fname: String,
            lname: String,
            address: String,
            tel: String,
            imageURL: String
        ) {
            this.id = id
            this.username = username
            this.email = email
            this.fname = fname
            this.lname = lname
            this.address = address
            this.tel = tel
            this.imageURL = imageURL
        }
    }
}