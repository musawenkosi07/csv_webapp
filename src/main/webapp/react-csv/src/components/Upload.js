import React, {Component} from "react";

import {Card, Form, Button, Col} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-native-fontawesome";
import {faPlusSquare, faSave, faUndo} from "@fortawesome/free-solid-svg-icons";
import ToastBox from "./ToastBox";
import axios, { post } from "axios";


export default class Upload extends Component{

    constructor(){
        super();
        this.state = {
            selectedFile:'',
        }

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        this.setState({
            selectedFile: event.target.files[0],
        })
    }

    submit(){
        const data = new FormData()
        data.append('file', this.state.selectedFile)
        console.warn(this.state.selectedFile);
        let url = "http://localhost:8080/api/student/upload";

        axios.post(url, data, { // receive two parameter endpoint url ,form data
        })
            .then(res => { // then print response status
                console.warn(res);
            })

    }

    render() {

        return (
            <div>
                <div style={{"display":this.state.show ? "block": "none"}}>
                    <ToastBox show = {this.state.show} message ={this.state.method === "post" ? "Csv Uploaded Successfully." : "Csv Not Uploaded Successfully "} type = {"success"}/>
                </div>
                <Card className={"border border-dark bg-dark text-white mx-auto"} style={{ width: '18rem' }}>
                    <Card.Header><FontAwesomeIcon icon={faPlusSquare}/>Upload</Card.Header>
                    <Form>
                        <Card.Body>
                            <label className="text-white">Select Csv File :</label>
                            <input type="file" className="form-control" name="upload_file" onChange={this.handleInputChange} />

                        </Card.Body>
                        <Card.Footer style={{"textAlign":"right"}}>
                            <button type="submit" className="btn btn-success" onClick={()=>this.submit()}><FontAwesomeIcon icon={faSave}/> Upload</button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>


        );
    }

}