import React, { useEffect, useState } from 'react';
import { Container } from './style.js';
import { Button, Card, Table } from 'react-bootstrap/';
import api from '../../../services/api';

function UserAdd() {
    const [items, setItems] = useState([]);
    const [editMode, setEditMode] = useState(false);
    const [editedItems, setEditedItems] = useState([]);
    const [editIndex, setEditIndex] = useState(-1);

    const fetchItems = async () => {
        api.get('/usuarios').then((response) => {
            console.log(response.data);
            setItems(response.data);
            setEditedItems(response.data);
        });
    };

    useEffect(() => {
        fetchItems();
    }, []);

    const handleEditClick = (index) => {
        setEditMode(true);
        setEditIndex(index);
    };

    const handleSaveClick = () => {
        setEditMode(false);
        setEditIndex(-1);
        console.log('Itens editados:', editedItems);
    };

    const handleItemChange = (index, field, value) => {
        setEditedItems((prevItems) =>
            prevItems.map((item, i) =>
                i === index ? { ...item, [field]: value } : item
            )
        );
    };

    return (
        <Container>
            <div className='container'>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Gênero</th>
                        <th>Data Nascimento</th>
                        <th>CEP</th>
                        <th>Numero</th>
                        <th>Ação</th>
                    </tr>
                    </thead>
                    <tbody>
                    {editedItems.map((item, index) => (
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={item.nome}
                                        onChange={(e) =>
                                            handleItemChange(index, 'nome', e.target.value)
                                        }
                                    />
                                ) : (
                                    item.nome
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={item.email}
                                        onChange={(e) =>
                                            handleItemChange(index, 'email', e.target.value)
                                        }
                                    />
                                ) : (
                                    item.email
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={item.genero}
                                        onChange={(e) =>
                                            handleItemChange(index, 'genero', e.target.value)
                                        }
                                    />
                                ) : (
                                    item.genero
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={item.dataNascimento}
                                        onChange={(e) =>
                                            handleItemChange(index, 'dataNascimento', e.target.value)
                                        }
                                    />
                                ) : (
                                    item.dataNascimento
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={item.cep}
                                        onChange={(e) =>
                                            handleItemChange(index, 'cep', e.target.value)
                                        }
                                    />
                                ) : (
                                    item.cep
                                )}
                            </td>
                            <td>
                                {editIndex === index ? (
                                    editMode ? (
                                        <Button variant="primary" onClick={handleSaveClick}>Salvar</Button>
                                    ) : (
                                        <Button variant="secondary" onClick={() => handleEditClick(index)}>Editar</Button>
                                    )
                                ) : null}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>
        </Container>
    );
}

export default UserAdd;
