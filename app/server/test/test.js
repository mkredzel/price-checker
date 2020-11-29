const chai = require("chai");
const chaiHttp = require("chai-http");
const server = require("../server");
const should = chai.should();
const { assert } = require('chai')
chai.use(chaiHttp);

//Wrapper for all server tests
describe("Server", function () {
  //Mocha/Chai test of RESTful Web Service
  describe("Connection to DB", () => {
    it("should GET all the TVS", (done) => {
      chai
        .request(server)
        .get("/tvs")
        .end((err, res) => {
          res.should.have.status(200);

          done();
        });
    });
  });

  //Mocha/Chai test of RESTful Web Service
  describe("GET /tvs", () => {
    it("should GET all the TVS Array", (done) => {
      chai
        .request(server)
        .get("/tvs")
        .end((err, res) => {
          JSON.parse(res.text).should.be.a("array");
          done();
        });
    });
  });

  //Mocha/Chai test of RESTful Web Service
  describe("GET /comparison", () => {
    it("should GET all the Comparison Array", (done) => {
      chai
        .request(server)
        .get("/tvs")
        .end((err, res) => {
          JSON.parse(res.text).should.be.a("array");
          done();
        });
    });
  });

  //Mocha/Chai test of RESTful Web Service
  describe("GET /tvs", () => {
    it("Random TV should have key: id that is a number", (done) => {
      chai
        .request(server)
        .get("/tvs")
        .end((err, res) => {
          let tvs = JSON.parse(res.text);
          let randomTV = Math.floor(Math.random() * tvs.length);
          tvs[randomTV].should.have.property(
            "id"
          );

          assert.isNumber(tvs[randomTV].id)
          done();
        });
    });
  });

  //Mocha/Chai test of RESTful Web Service
  describe("GET /tvs", () => {
    it("Random TV should have key: screen_size and it is expected to be a string", (done) => {
      chai
        .request(server)
        .get("/tvs")
        .end((err, res) => {
          let tvs = JSON.parse(res.text);
          let randomTV = Math.floor(Math.random() * tvs.length);
          tvs[randomTV].should.have.property(
            "screen_size"
          ).to.be.a('string');
          done();
        });
    });
  });
});
