html, body {
  height: 100%;
  margin: 0;
}

#content {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

#main {
  flex: 1;
}

footer {
  background-color: #000;
  color: #fff;
  text-align: center;
  padding: 1em 0;
}

.top-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #000;
  color: #fff;
  padding: 0.5em 1em;
}

.social-links a,
.contact-info a {
  color: #fff;
  margin-right: 0.5em;
  text-decoration: none;
}

.contact-info {
  display: flex;
  align-items: center;
}

.contact-info span {
  margin: 0 0.5em;
}

.social-links a:hover,
.contact-info a:hover {
  text-decoration: underline;
}

