const express = require("express")
const bodyParser = require("body-parser")
const udp = require("dgram")
const RST_PORT = 8888
const RST_SERVER = "192.168.4.1"

app = express()
server = udp.createSocket('udp4')

app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())
app.use(express.static("www"))

function api(req, res){
	let command = ""

	if(req.body.Left == "true"){
		command = "A"
	}else if(req.body.Right == "true"){
		command = "B"
	}else{
		command = "D"
	}

	if(req.body.Forward == "true"){
		command += "5"
	}else if(req.body.Reverse == "true"){
		command += "2"
	}else{
		command += "4"
	}

	if(command != "D4"){
		console.log("[+] Transmitting....")
		server.send(command, RST_PORT, RST_SERVER)
	}
	res.end()
}


app.post("/api", api)

app.listen(8000 , () => {
	console.log("[ONLINE]")
})
