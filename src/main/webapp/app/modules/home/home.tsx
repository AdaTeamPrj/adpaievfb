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
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h2>
          <Translate contentKey="home.title">Welcome, Java Hipster!</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">This is your homepage</Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div></div>
        )}

        <div className="wrapper">
          <ul className="body-link">
            <a href="#" title="Accueil">
              <br />
              <div className="item">Gestion de paie</div>
            </a>
          </ul>

          <ul className="body-link">
            <a href="#" title="Accueil">
              <br />
              <div className="item">Declarations sociales</div>
            </a>
          </ul>
          <ul className="body-link">
            <a href="#" title="Accueil">
              <br />
              <div className="item">Gestion embauches</div>
            </a>
          </ul>

          <ul className="body-link">
            <a href="#" title="Accueil">
              <br />
              <div className="item">Gestion des temps</div>
            </a>
          </ul>

          <ul className="body-link">
            <a href="#" title="Accueil">
              <br />
              <div className="item">Tableaux de bord</div>
            </a>
          </ul>
        </div>
      </Col>
    </Row>
  );
};

export default Home;
