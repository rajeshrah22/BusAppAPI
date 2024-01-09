import React from 'react'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemText from '@mui/material/ListItemText'
import ListItemAvatar from '@mui/material/ListItemAvatar'
import Avatar from '@mui/material/Avatar'
import { useTheme } from '@emotion/react'
import Paper from '@mui/material/Paper'
import ArrowForwardIcon from '@mui/icons-material/ArrowForward'
import IconButton from '@mui/material/IconButton'

const Link = ({ primary, secondary, handleClick }) => {
  const theme = useTheme();
  return (
    <>
      <Paper
        elevation={0}
        sx={{
          my: 1,
        }}
      >
        <ListItem
          sx={{
            borderRadius: 1,
            width: '100%',
          }}
        >
          <ListItemAvatar>
            <Avatar
              sx={{
                backgroundColor: theme.palette.primary.main
              }}
            >
              {primary[0].toUpperCase()}
            </Avatar>
          </ListItemAvatar>
          <ListItemText primary={primary} secondary={secondary}></ListItemText>
          <IconButton
            onClick={() => handleClick(primary)}
          >
            <ArrowForwardIcon />
          </IconButton>
        </ListItem>
      </Paper>
    </>
  )
}
export const MenuList = ({ list, handleClick}) => {
  return (
    <>
      <List
        sx={{
          p: 0,
        }}
      >
        {list.map((agency, index) => {
          return (
            <Link
              primary={agency.tag}
              secondary={agency.regionTitle}
              handleClick={handleClick}
              key={index} />
          );
        })}
      </List>
    </>
  )
}

export default MenuList
