//LIBRERIAS DE MONGO
const express = require('express')
const mongoose = require('mongoose')
const {MongoClient} = require('mongodb')
const client = new MongoClient('mongodb://localhost:27017')
const app = express()
const path = require('path')
const db = client.db('KINDER')
const matriculacion = db.collection('matriculacion')

app.use(express.urlencoded({extended:false})); 
app.use(express.json())
app.use(express.static('public'))


app.get('/', function(req, res) {
    res.sendFile(path.join(__dirname + '/public/matriculacion.html'));
});

app.post('/index', async function(req, res){
    await client.connect();
    const {id_nino,fecha_matricula,graduado,retirado,id_matricula}=req.body;
    //guardamos la ejecución del trigger en la variable
   // let trigger = TG_impidematricula(id_nino)
    //console.log(trigger)
try{
     
    if (TG_impidematricula(id_nino)) {
        console.log('El alumno ya se encuentra registrado');    
    } else {
                //insertamos en mongo
        matriculacion.insertOne({retirado, graduado, fecha_matricula: new Date(fecha_matricula),id_matricula,id_nino}
            ,(error, datos)=>{
                if(datos){
                    res.json({insert: 'ok'})
                }
            })      
    }
}catch(err){
    throw err
}
})


app.listen(3000, () => console.log('Server on port 3000'))

const TG_impidematricula = async(id) =>{
    let num_matriculas;
    let maximo = 1;
    matriculacion.aggregate(
        [
                {
                    $match:  { 
                        "id_nino": id
                    }
                },
                {
                    $group: {
                    _id: "id_nino",
                    fallas: { $count : {}  }
                    }
                }
        ]
    ).toArray((error, datos) => {
            if(datos.length>0){
                
            num_matriculas = datos[0].fallas;
           // console.log(datos[0].fallas)
            //console.log(num_matriculas>=maximo)
            }
            if(num_matriculas>=maximo){
                return true
            }else{
                return false
            }
        })
    }
  

