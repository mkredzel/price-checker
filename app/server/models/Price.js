const Sequelize = require("sequelize");
const sequelize = require("../database/connection");

const Price = sequelize.define(
  "price",
  {
    id: {
      type: Sequelize.INTEGER(11),
      allowNull: false,
      autoIncrement: true,
      primaryKey: true
    },
    amount: Sequelize.FLOAT
  },
  {
    timestamps: false,
  }
);
module.exports = Price;
