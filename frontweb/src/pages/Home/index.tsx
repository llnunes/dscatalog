import {ReactComponent as MainImage} from 'assets/images/main-image.svg';

import Navbar from 'components/Navbar';
import ButtonIcon from 'components/Navbar/ButtonIcon';
import './styles.css'

const Home = () => {
  return (
    <>
        <Navbar></Navbar>
        <div className='home-container'>
            <div className='base-card home-card'>
                <div className='home-content-container'>
                    <div>
                        <h1>Conheça o melhor catalogo de produtos</h1>
                        <p>Ajudaremos você a encontrar os melhores produtos disponíveis no mercado.</p>
                    </div>
                    <ButtonIcon></ButtonIcon>
                </div>
                <div className='home-image-container'>
                    <MainImage />
                </div>

            </div>
        </div>
    </>
  );
}

export default Home;