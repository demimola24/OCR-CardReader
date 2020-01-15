package com.ajocardreader.models

data class CardVerificationResponse (

	var number : Number?,
	var scheme : String,
	var type : String,
	var brand : String,
	var prepaid : Boolean,
	var country : Country?,
	var bank : Bank?
)

data class Number (
	val length : Int,
	val luhn : Boolean
)

data class Country (

	val numeric : Int,
	val alpha2 : String,
	val name : String,
	val emoji : String,
	val currency : String,
	val latitude : Int,
	val longitude : Int
)

data class Bank (

	val name : String,
	val url : String,
	val phone : Int,
	val city : String
)