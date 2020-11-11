const Sequelize = require("sequelize");
const sequelize = require("../database/connection");

const TV = sequelize.define(
  "tv",
  {
    id: {
      type: Sequelize.INTEGER(11),
      allowNull: false,
      autoIncrement: true,
      primaryKey: true,
    },
    brand: Sequelize.INTEGER(255),
    model: Sequelize.INTEGER(255),
    screen_size: Sequelize.STRING(6),
    description: Sequelize.TEXT,
    image_url: Sequelize.STRING(2083),
  },
  {
    timestamps: false,
  }
);
module.exports = TV;
