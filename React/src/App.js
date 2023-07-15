import logo from './logo.svg';
import './App.css';
import Header from './components/Header';
import { ProductComponent } from './components/Products';
import { UserAdd } from './components/Users/Users';
function App() {
	return (
		<>
			<Header />
			<ProductComponent />
		</>
	);
}

export default App;
