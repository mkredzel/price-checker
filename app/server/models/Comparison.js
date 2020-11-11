const Sequelize = require("sequelize");
const sequelize = require("../database/connection");

const Comparison = sequelize.define(
  "comparison",
  {
    id: {
      type: Sequelize.INTEGER(11),
      allowNull: false,
      autoIncrement: true,
      primaryKey: true,
    },
    tv_id: Sequelize.STRING(255),
    price: Sequelize.FLOAT,
    url: Sequelize.STRING(2083),
  },
  {
    timestamps: false,
  }
);
module.exports = Comparison;
