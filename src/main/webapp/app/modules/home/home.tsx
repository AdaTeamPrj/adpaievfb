import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import GridLayout from 'react-grid-layout';
import styled from 'styled-components';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <section id="hero">
        <div className="container">
          <div className="row">
            <div className="col-sm-6">
              <br></br>
              <br></br>
              <br></br>
              <p className="kicker">Logiciel de paie</p>
              <h1 className="jumbo">Think Positive, Think Adateam</h1>

              <p className="phrase">Nous proposons une solution pour faciliter votre gestion de la paie et vous faire gagner de temps.</p>

              <a href="/register" className="btn btn-primary mr-sm">
                <i className="fa-solid fa-download"></i> &nbsp;Connexion
              </a>

              <a href="/account/register" className="btn btn-secondary">
                <i className="fa-brands fa-github"></i>
                &nbsp;Créer un compte
              </a>
            </div>

            <div className="col-sm-6">
              <img
                src="https://empxtrack.com/wp-content/uploads/2021/04/best-payroll-system-for-small-businesses.png"
                className="hero-img"
                alt=""
              ></img>
            </div>
          </div>

        </div>
      </section>

      <div className="wrapper">
        <ul className="body-link">
          <a href="/fiche-de-paie" title="Fiche de paie">
            <br />
            <div className="item">Gestion de paie</div>
          </a>
        </ul>
        <ul className="body-link">
          <a href="/base-taux-cotisation" title="Déclaration sociales">
            <br />
            <div className="item">Declarations sociales</div>
          </a>
        </ul>
        <ul className="body-link">
          <a href="/contrat" title="Embauches">
            <br />
            <div className="item">Gestion embauches</div>
          </a>
        </ul>
        <ul className="body-link">
          <a href="/conge" title="Congés">
            <br />
            <div className="item">Gestion des congés</div>
          </a>
        </ul>
        <ul className="body-link">
          <a href="#" title="Tableaux de bord">
            <br />
            <div className="item">Tableaux de bord</div>
          </a>
        </ul>
      </div>
    </Row>
  );
};

export default Home;
