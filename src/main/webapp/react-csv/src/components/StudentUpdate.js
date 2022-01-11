import React, {Component} from "react";

import {Card, Form, Button, Col} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-native-fontawesome";
import {faEdit, faList, faPlusSquare, faSave, faUndo} from "@fortawesome/free-solid-svg-icons";
import ToastBox from "./ToastBox";
import axios from "axios";


export default class StudentUpdate extends Component{

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state.show = false;
        this.studentChange = this.studentChange.bind(this);
        this.submitStudent = this.submitStudent.bind(this);
    };

    initialState = {studentNumber:'', firstName:'', lastName:'', courseCode:'', courseDescription:'', grade:''}

    componentDidMount() {
        const studentNumber = +this.props.match.params.studentNumber;
        if (studentNumber){
            this.findStudentById(studentNumber);
        }
    }

    findStudentById = (studentNumber, courseCode) => {

        axios.get("http://localhost:8080/api/student/"+studentNumber+"/"+courseCode)
            .then(response => {
                if (response.data != null){
                    this.setState({
                        studentNumber: response.data.studentNumber,
                        firstName: response.data.firstName,
                        lastName: response.data.lastName,
                        courseCode: response.data.courseCode,
                        courseDescription: response.data.courseDescription,
                        grade: response.data.grade,

                    });
                }
            }).catch((error) => {
            console.error("Error - "+error);
        });

    }

    resetStudent = () =>  {
        this.setState(() => this.initialState);
    };




    updateStudent = event => {
        event.preventDefault();

        const student = {
            studentNumber: this.state.studentNumber,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            courseCode: this.state.courseCode,
            courseDescription: this.state.courseDescription,
            grade: this.state.grade

        };

        axios.patch("http://localhost:8080/api/student/"+student.studentNumber+"/"+student.courseCode,student)
            .then(response =>{
                if(response.data != null){
                    this.setState({"show": true, "method":"patch"});
                    setTimeout(() =>  this.setState({"show":false}),3000);
                    setTimeout(() =>  this.studentList(),3000);
                }else {
                    this.setState({"show": false});
                }
            });
        this.setState(this.initialState);

    };

    studentChange = event => {
        this.setState({
            [event.target.name]:event.target.value
        });
    };

    studentList = () => {
        return this.props.history.push("/list");
    };

    render() {

        const {studentNumber, firstName, lastName, courseCode, courseDescription, grade} = this.state;
        return (
            <div>
                <div style={{"display":this.state.show ? "block": "none"}}>
                    <ToastBox show = {this.state.show} message ={"Student Saved Successfully."} type = {"success"}/>
                </div>
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Header><FontAwesomeIcon icon={faPlusSquare}/>Add New Student</Card.Header>
                    <Form onReset={this.resetStudent} onSubmit={this.submitStudent} id="studentFormId">
                        <Card.Body>
                            <Form.Group as={Col} controlId="formGridStudentNumber">
                                <Form.Label>Student Number</Form.Label>
                                <Form.Control required autoComplete={"off"}
                                              type="text" name="studentNumber"
                                              value={studentNumber}
                                              onChange={this.studentChange}
                                              className={"bg-dark text-white"}
                                              placeholder="Enter Student Number" />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridFirstName">
                                <Form.Label>First Name</Form.Label>
                                <Form.Control required autoComplete={"off"}
                                              type="text" name="firstName"
                                              value={firstName}
                                              onChange={this.studentChange}
                                              className={"bg-dark text-white"}
                                              placeholder="Enter First Name" />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridLastName">
                                <Form.Label>Last Name</Form.Label>
                                <Form.Control required autoComplete={"off"}
                                              type="text" name="lastName"
                                              value={lastName}
                                              onChange={this.studentChange}
                                              className={"bg-dark text-white"}
                                              placeholder="Enter Last Name" />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridCourseCode">
                                <Form.Label>Course Code</Form.Label>
                                <Form.Control required autoComplete={"off"}
                                              type="text" name="courseCode"
                                              value={courseCode}
                                              onChange={this.studentChange}
                                              className={"bg-dark text-white"}
                                              placeholder="Enter Course Code" />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridCodeDescription">
                                <Form.Label>Course Description</Form.Label>
                                <Form.Control required autoComplete={"off"}
                                              type="text" name="courseDescription"
                                              value={courseDescription}
                                              onChange={this.studentChange}
                                              className={"bg-dark text-white"}
                                              placeholder="Enter Course Description" />
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridGrade">
                                <Form.Label>Grade</Form.Label>
                                <Form.Control required autoComplete={"off"}
                                              type="text" name="grade"
                                              value={ grade}
                                              onChange={this.studentChange}
                                              className={"bg-dark text-white"}
                                              placeholder="Enter Grade" />
                            </Form.Group>

                        </Card.Body>
                        <Card.Footer style={{"textAlign":"right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> Save
                            </Button>
                            <Button size="sm" variant="info" type="reset">
                                <FontAwesomeIcon icon={faUndo} /> Reset
                            </Button>
                            <Button size="sm" variant="info" type="button" onClick={this.studentList.bind()}>
                                <FontAwesomeIcon icon={faList} /> Student List
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>


        );
    }


}