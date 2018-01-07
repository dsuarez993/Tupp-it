'use strict'

const mongoose = require('mongoose')
const Food = require('../models/food')

function createFood(req, res){
  console.log('createFood')

  console.log(req.body)
  const food = new Food({
    title: req.body.title,
    image: req.body.image,
    description: req.body.description,
    price: req.body.price,
    location: {lat: req.body.latitude, long: req.body.longitude},
    userId: req.body.userId,
    date: Date.now(),
    status: "active"
  })

  food.save((err, foodStored)=>{
    if(err) res.status(500).send({message:`Error al crear comida en la base de datos: ${err}`})
    console.log(foodStored)
    res.status(200).send({food:foodStored})
  })
}

function updateFood(req,res) {

  console.log('updateFood')

  let foodId = req.params.foodId
  let update = req.body

  console.log(foodId)

  Food.findByIdAndUpdate(foodId, update, (err, foodUpdated)=>{
    if(err) return res.status(500).send({message:`Error al actualizar comida: ${err}`})

    console.log(foodUpdated)
    res.status(200).send({food: foodUpdated})
  })

}

function deleteFood(req,res) {

  console.log('delteFood')

  let foodId = req.params.foodId

  Food.findById(foodId, (err,food) => {
    if(err) return res.status(500).send({message:`Error al eliminar comida: ${err}`})

    food.remove(err=>{
      if(err) return res.status(500).send({message:`Error al eliminar comida: ${err}`})
      res.status(200).send({message:'La comida ha sido eliminada'})
    })
  })

  //TODO actualizar usuarios

}

function getFood(req, res){

  console.log('getFood')

  let foodId = req.params.foodId

  Food.findById(foodId, (err, food)=>{
    if(err) return res.status(500).send({message:`Error al realizar la peticion: ${err} `})
    if(!food) return res.status(404).send({message:'La comida no existe'})

    console.log(food)
    res.status(200).send({ food: food})
  })

}

function getFoodListByLocation(req, res){
  //let lat = req.params.lat
  //let long = req.params.long

  //TODO hacer query
  console.log('getFoodListByLocation')

  let pageitems = 15
  let page = req.params.page

  console.log(page)

  Food.find({}, (err,foods)=>{
    if(err) return res.status(500).send({message:`Error al realizar la peticion: ${err}`})
    if(!foods) return res.status(404).send({message:`No existen comidas`})

    //console.log({foods});
    console.log("size list: "+foods.length)
    console.log("enviando lista")
    res.status(200).send({foods})
  }).skip(page*pageitems).limit(pageitems)

}


module.exports = {
  createFood,
  updateFood,
  deleteFood,
  getFood,
  getFoodListByLocation
}
