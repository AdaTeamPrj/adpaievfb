import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

class Footer extends React.Component {
  render() {
    return (
      <div id="footer-wrap">
        <footer className="footer">
          <div className="top-footer">
            <div className="row">
              <div className="col-md-3">
                <div className="footer-logo">
                  <a href="/" title="Adateam">
                    <img src="content/images/Adateamlogo.png" width="75" alt="React Adateam" className="img-fluid" /> <br />
                    ADPaie
                  </a>
                  <p className="tagline">Think positive, think Adateam.</p>
                </div>
              </div>
              <div className="col-md-2">
                <h4>Lien</h4>
                <ul className="footer-link">
                  <li>
                    <a href="#" title="Accueil">
                      Accueil
                      <br />
                      Gestion fiches de paie
                      <br />
                      Déclarations sociales
                      <br />
                      Gestion des embauches
                      <br />
                    </a>
                  </li>
                </ul>
              </div>
              <div className="col-md-2">
                <h4>À Propos</h4>
                <ul className="footer-link">
                  <li>
                    <a href="#" title="Faq">
                      Tutoriels & FAQ
                      <br />
                      Site web
                    </a>
                  </li>
                  <li></li>
                </ul>
              </div>
              <div className="col-md-3">
                <h4>Nous Contacter</h4>
                <ul className="footer-link">
                  <li>
                    <a href="mail-to:adateamprj@gmail.com" title="Contact">
                      adateamprj@gmail.com
                    </a>
                  </li>
                  <li>
                    <br />
                    <br />
                    <img src="content/images/Adateamlogo.png" width="75" alt="React Adateam" className="img-fluid" />{' '}
                    <img src="content/images/Adaminglogo.jpg" width="75" alt="React Adateam" className="img-fluid" />{' '}
                    <img src="content/images/Intilogo.png" width="75" alt="React Adateam" className="img-fluid" />
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="bottom-footer">
            <div className="row">
              <div className="col-md-5">
                <p className="copyright pt-3">Copyright &copy; 2022 ADPaie by Adateam</p>
              </div>
            </div>
          </div>
        </footer>
      </div>
    );
  }
}
export default Footer;
