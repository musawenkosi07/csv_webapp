import React, {Component} from "react";

import {Navbar, Nav} from "react-bootstrap";
import {Link} from "react-router-dom";


export default class NavigationBar extends Component{
    render(){
        return(
            <Navbar bg="dark" variant="dark">
                <Link to={""} className="navbar-brand">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/d/d2/File-text-dynamic-gradient.png" width="25" height="25" alt="brand"/> CSV WebApp
                </Link>
                <Nav className="me-auto">
                    <Link to={"/upload"} className="nav-link">Upload Csv</Link>
                    <Link to={"/add"} className="nav-link">Add Student</Link>
                    <Link to={"/list"} className="nav-link">Student List</Link>
                </Nav>
            </Navbar>
        );
    }
}
