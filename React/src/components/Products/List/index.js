import React, { useEffect, useState } from 'react';
import { Container } from './style.js';
import { Button, Table } from 'react-bootstrap/';
import api from '../../../services/api';

function ProductComponent() {
    const [products, setProducts] = useState([]);
    const [editMode, setEditMode] = useState(false);
    const [editedProducts, setEditedProducts] = useState([]);
    const [editIndex, setEditIndex] = useState(-1);

    const fetchProducts = async () => {
        api.get('/products?limit=100').then((response) => {
            console.log(response.data);
            setProducts(response.data);
            setEditedProducts(response.data);
        });
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    const handleEditClick = (index) => {
        setEditMode(true);
        setEditIndex(index);
    };

    const handleSaveClick = () => {
        setEditMode(false);
        setEditIndex(-1);
        console.log('Itens editados:', editedProducts);
    };

    const handleItemChange = (index, field, value) => {
        setEditedProducts((prevProducts) =>
            prevProducts.map((product, i) =>
                i === index ? { ...product, [field]: value } : product
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
                        <th>Descrição</th>
                        <th>Categoria</th>
                        <th>Preço</th>
                        <th>Desconto</th>
                        <th>Imagem</th>
                        <th>Marca</th>
                        <th>Fabricante ID</th>
                        <th>Princípio Ativo</th>
                        <th>Classificação</th>
                        <th>Via de Administração</th>
                        <th>Indicação</th>
                        <th>Ação</th>
                    </tr>
                    </thead>
                    <tbody>
                    {editedProducts.map((product, index) => (
                        <tr key={product.id}>
                            <td>{product.id}</td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.descricao}
                                        onChange={(e) =>
                                            handleItemChange(index, 'descricao', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.descricao
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.categoria}
                                        onChange={(e) =>
                                            handleItemChange(index, 'categoria', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.categoria
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='number'
                                        value={product.preco}
                                        onChange={(e) =>
                                            handleItemChange(index, 'preco', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.preco
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='number'
                                        value={product.desconto}
                                        onChange={(e) =>
                                            handleItemChange(index, 'desconto', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.desconto
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.imagem}
                                        onChange={(e) =>
                                            handleItemChange(index, 'imagem', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.imagem
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.marca}
                                        onChange={(e) =>
                                            handleItemChange(index, 'marca', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.marca
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='number'
                                        value={product.fabricanteId}
                                        onChange={(e) =>
                                            handleItemChange(index, 'fabricanteId', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.fabricanteId
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.principioAtivo}
                                        onChange={(e) =>
                                            handleItemChange(index, 'principioAtivo', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.principioAtivo
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.classificacao}
                                        onChange={(e) =>
                                            handleItemChange(index, 'classificacao', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.classificacao
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.viaAdministracao}
                                        onChange={(e) =>
                                            handleItemChange(index, 'viaAdministracao', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.viaAdministracao
                                )}
                            </td>
                            <td>
                                {editIndex === index && editMode ? (
                                    <input
                                        type='text'
                                        value={product.indicacao}
                                        onChange={(e) =>
                                            handleItemChange(index, 'indicacao', e.target.value)
                                        }
                                    />
                                ) : (
                                    product.indicacao
                                )}
                            </td>
                            <td>
                                {editIndex === index ? (
                                    editMode ? (
                                        <Button variant='primary' onClick={handleSaveClick}>Salvar</Button>
                                    ) : (
                                        <Button variant='secondary' onClick={() => handleEditClick(index)}>Editar</Button>
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

export default ProductComponent;
