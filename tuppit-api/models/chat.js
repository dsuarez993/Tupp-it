'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const ChatSchema = new Schema({
  foodId: {type: Schema.Types.ObjectId, ref: 'Food'},
  users: {type:[{
    userId:{type: Schema.Types.ObjectId, ref: 'User'},
    displayName:String
  }], unique:true},
  messages: [{
    userId:{type: Schema.Types.ObjectId, ref: 'User'},
    message:String,
    sendedDate:{type:Date, default: Date.now()}
  }]
});

module.exports= mongoose.model('Chat', ChatSchema)
