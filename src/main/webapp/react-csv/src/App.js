import React from "react";
import './App.css';



import {Container, Row, Col} from "react-bootstrap";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom"

import NavigationBar from "./components/NavigationBar";
import Welcome from "./components/Welcome";
import StudentAdd from "./components/StudentAdd";
import StudentList from "./components/StudentList";
import Footer from "./components/Footer";
import Upload from "./components/Upload";
import StudentUpdate from "./components/StudentUpdate";

function App() {
    const marginTop = {
        marginTop:"20px"
    };

  return (
    <Router>
        <NavigationBar/>
       <Container>
           <Row>
               <Col lg={12} style={marginTop}>
                   <Switch>
                       <Route path="/" exact component={Welcome}/>
                       <Route path="/upload" exact component={Upload}/>
                       <Route path="/add" exact component={StudentAdd}/>
                       <Route path="/edit/:studentNumber/:courseCode" exact component={StudentUpdate}/>
                       <Route path="/list" exact component={StudentList}/>
                   </Switch>
               </Col>
           </Row>
       </Container>

        <Footer/>
    </Router>
  );
}

export default App;
