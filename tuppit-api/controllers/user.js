'use strict'

const mongoose = require('mongoose')
const User = require('../models/user')

function createUser(req, res){
  console.log('createUser')
  console.log(req.body.displayName)

  const user = new User({
    email: req.body.email,
    displayName: req.body.displayName,
    password: req.body.password,
    avatar: req.body.avatar,
    description: req.body.description,
    tokenId: req.body.tokenId,
    singupDate: Date.now()
  })

  User.findOne({'email':user.email}, (err, existentUser)=>{
    if(existentUser) return res.status(500).send({message:`Error al crear usuario, email ya existente: ${err} `})

    user.save((err, userStored)=>{
      if(err) res.status(500).send({message:`Error al crear el usuario en la base de datos: ${err}`})

      res.status(200).send({user:userStored})
    })
  })


}

function updateUser(req,res) {

  let userId = req.params.userId
  let update = req.body

  console.log(update)

  User.findByIdAndUpdate(userId, update, (err, userUpdated)=>{
    if(err) return res.status(500).send({message:`Error al actualizar usuario: ${err}`})

    console.log(userUpdated)
    res.status(200).send({message:'Usuario actualizado', updated:update})
  })

}

function deleteUser(req,res) {

  let userId = req.params.userId

  User.findById(userId, (err,user) => {
    if(err) return res.status(500).send({message:`Error al eliminar usuario: ${err}`})
    if(!user) return res.status(404).send({message:`Error al eliminar usuario: no existe`})
    user.remove(err=>{
      if(err) return res.status(500).send({message:`Error al eliminar usuario: ${err}`})
      res.status(200).send({message:'El usuario ha sido eliminado'})
    })
  })

}

function getUser(req, res){

  let userId = req.params.userId
  console.log('getUser with ID: '+userId)

  User.findById(userId, (err, user)=>{
    if(err) return res.status(500).send({message:`Error al realizar la peticion: ${err} `})
    if(!user) return res.status(404).send({message:'El usuario no existe'})

    res.status(200).send({ user: user})
  })

}

function loginUser(req, res){

  //TODO hacer seguro el login usando web tokens
  const email = req.params.email
  const password = req.params.password

  console.log('Login-->'+email+' '+password)

  User.findOne({'email':email, 'password':password}, (err, user)=>{
    if(err) return res.status(500).send({message:`Error al realizar la peticion: ${err} `})
    if(!user) return res.status(404).send({message:'El email introducido no existe o password incorrecta'})

    res.status(200).send({ user: user})
  })

}

function updateTokenId(req,res) {

  let userId = req.params.userId
  let token = req.body.tokenId

  console.log(token)

  User.findByIdAndUpdate(userId, { $set: { tokenId: token }}, (err, userUpdated)=>{
    if(err) return res.status(500).send({message:`Error al actualizar usuario: ${err}`})

    console.log(userUpdated)
    res.status(200).send({message:'Usuario actualizado', userUpdated})
  })

}

module.exports = {
  createUser,
  updateUser,
  deleteUser,
  getUser,
  loginUser,
  updateTokenId
}
