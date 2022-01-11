import React, {Component} from "react";

import {Card, Table, ButtonGroup, Button, Nav} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-native-fontawesome";
import {faEdit, faList, faTrash} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import ToastBox from "./ToastBox";
import {Link} from "react-router-dom";

export default class StudentList extends Component{

    constructor(props) {
        super(props);
        this.state = {
          students : []
        };
    }
    componentDidMount() {
        this.getAllStudents();
    }

    getAllStudents() {
        axios.get("http://localhost:8080/api/student/all")
            .then(response => response.data)
            .then(data => {
                this.setState({students: data})
            });
    }

    deleteStudent = (studentNumber, courseCode) => {
        axios.delete("http://localhost:8080/api/student/"+studentNumber+"/"+courseCode)
            .then(response => {
                if(response.data != null){
                    this.setState({"show": true});
                    setTimeout(() =>  this.setState({"show": false}),3000);
                    this.setState({
                        students: this.state.students.filter(student => student.studentNumber !== studentNumber)
                    });
                }else {
                    this.setState({"show": false});
                }
            });
    }

    render() {
        return (
            <div>
                <div style={{"display":this.state.show ? "block": "none"}}>
                    <ToastBox show={ this.state.show} message ={"Student Deleted Successfully."} type={"danger"}/>
                </div>
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Header><FontAwesomeIcon icon={faList} /> Student List</Card.Header>
                    <Card.Body>
                        <Table bordered hover striped variant="dark">
                            <thead>
                            <tr>
                                <th>Student Number</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Course Code</th>
                                <th>Course Description</th>
                                <th>Grade</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.state.students.length === 0 ?
                                <tr align="center">
                                    <td colSpan="7">Students Available.</td>
                                </tr> :
                                this.state.students.map((student) => (
                                    student.courses.map((course) => (
                                    <tr key={student.id}>
                                        <td>{student.studentNumber}</td>
                                        <td>{student.firstName}</td>
                                        <td>{student.lastName}</td>
                                        <td>{course.courseCode}</td>
                                        <td>{course.courseDescription}</td>
                                        <td>{course.grade}</td>
                                        <td>
                                            <ButtonGroup>
                                                <Link to={"edit/"+student.studentNumber} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{''}
                                                {/*<Button size="sm" variant="outline-primary"><FontAwesomeIcon icon={faEdit} /></Button>*/}
                                                <Button size="sm" variant="outline-danger" onClick={this.deleteStudent.bind(this,student.studentNumber,course.courseCode)}><FontAwesomeIcon icon={faTrash} /></Button>{''}
                                            </ButtonGroup>
                                        </td>
                                    </tr>

                                ))))

                            }
                            </tbody>
                        </Table>
                    </Card.Body>
                </Card>
            </div>

        );
    }
}
