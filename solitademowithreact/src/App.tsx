import { useEffect, useState } from 'react';
import axios from 'axios';

import { Names } from './types';

const App = () =>  {
  const [namesData, setNamesData] = useState< Names | undefined>(undefined);
  const [searchName, setSearchName] = useState< string >('');


  useEffect(() => {
    const fetchNameData = async () => {
      try {
        const { data: nameData } = await axios.get<Names>(
          'https://raw.githubusercontent.com/solita/dev-academy-2021/main/names.json'
        );
        setNamesData(nameData);
      } catch (error) {
        console.log(error);
      }
    };
    fetchNameData();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

// The code below is for demonstrative purposes only. Normally this code would be partioned into smaller modules, which would do only one thing. 
// The business logic of sorting and manipulating data would be separate, unit testable functions.
  return (
    <div className="App">
      <header className="App-header">
      NamesApp
      </header>
      <h1>Names ordered by amount in descending order </h1>
      {namesData === undefined ? null :
        <ul>
          {namesData.names.sort((a, b) => Number(b.amount) - Number(a.amount)).map(n => <li key={n.name}>{n.name} {n.amount}</li>)}
        </ul>
        }
        <h1>Names ordered by name in alphabetical order </h1>
      {namesData === undefined ? null :
        <ul>
          {namesData.names.sort((a, b) => a.name.localeCompare(b.name)).map(n => <li key={n.name}>{n.name} {n.amount}</li>)}
        </ul>
        }
      {namesData === undefined ? null :
        <h1>Amount of all names: {namesData.names.reduce((sum, name) => sum + name.amount, 0)}</h1>}
      {namesData === undefined ? null : 
      <div>
        <h1>Get amount by name:</h1>
        <input
          id='name'
          type='text'
          value={searchName}
          name='name'
          placeholder='type name here'
          onChange={({ target }) => setSearchName(target.value)}
        />
        {searchName.length > 3 ? <h3>amount of {searchName.substr(0,1).toLocaleUpperCase()}{searchName.substr(1)}: 
        {namesData.names.find(n => n.name.toLocaleUpperCase() === searchName.toLocaleUpperCase())?.amount}</h3> : null}
      </div>}
    </div>
  );
}

export default App;
