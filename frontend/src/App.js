import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Header from './component/common/Header';
import Catalog from './component/features/Catalog';

function App() {
  return (
      <div>
        <Router>
          <div className="">
            <Header />
            <div>
              <Switch>
                <Route path="/" exact component={Catalog}></Route>
                <Route path="/catalog" component={Catalog}></Route>
              </Switch>
            </div>
          </div>
        </Router>
      </div>
  );
}

export default App;
