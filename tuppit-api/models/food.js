'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const FoodSchema = new Schema({
  title: String,
  image: String,
  description: String,
  price: Number,
  location: {lat: Number, long: Number},
  userId: {type: Schema.Types.ObjectId, ref: 'User'},
  date: Date,
  status: String
})

module.exports= mongoose.model('Food', FoodSchema)
